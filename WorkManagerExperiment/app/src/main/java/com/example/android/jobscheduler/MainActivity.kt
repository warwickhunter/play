/*
 * Copyright 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.jobscheduler

import android.Manifest
import android.app.Activity
import android.app.job.JobScheduler
import android.content.ComponentName
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.os.*
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.telephony.SmsManager
import android.util.Log
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.RadioButton
import androidx.work.*
import java.util.*
import java.util.concurrent.TimeUnit

private const val TO = "+61412341234" // Replace with a real phone number

private const val REQUEST_SEND_SMS = 42

/**
 * Schedules and configures work to be executed by a [WorkManager].
 *
 * [MyJobService] can send messages to this via a [Messenger]
 * that is sent in the Intent that starts the Service.
 *
 * This is a modified version of the JobScheduler example app.
 * It has been modified by Warwick Hunter.
 */
class MainActivity : Activity() {

    lateinit private var anyConnectivityRadioButton: RadioButton
    lateinit private var deadlineEditText: EditText
    lateinit private var delayEditText: EditText
    lateinit private var durationTimeEditText: EditText
    lateinit private var requiresChargingCheckBox: CheckBox
    lateinit private var requiresIdleCheckbox: CheckBox
    lateinit private var wiFiConnectivityRadioButton: RadioButton

    // Handler for incoming messages from the service.
    lateinit private var handler: IncomingMessageHandler
    lateinit private var serviceComponent: ComponentName
    private var jobId = Optional.empty<UUID>()

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.sample_main)

        anyConnectivityRadioButton = findViewById(R.id.checkbox_any)
        deadlineEditText = findViewById(R.id.deadline_time)
        delayEditText = findViewById(R.id.delay_time)
        durationTimeEditText = findViewById(R.id.duration_time)
        requiresChargingCheckBox = findViewById(R.id.checkbox_charging)
        requiresIdleCheckbox = findViewById(R.id.checkbox_idle)
        wiFiConnectivityRadioButton = findViewById(R.id.checkbox_unmetered)

        handler = IncomingMessageHandler(this)
        serviceComponent = ComponentName(this, MyJobService::class.java)

        findViewById<Button>(R.id.cancel_button).setOnClickListener { cancelAllWork() }
        findViewById<Button>(R.id.finished_button).setOnClickListener { finishJob() }
        findViewById<Button>(R.id.schedule_button).setOnClickListener {
            val permission = Manifest.permission.SEND_SMS
            if (ContextCompat.checkSelfPermission(this, permission) != PERMISSION_GRANTED) {
                // Permission is not granted, must request it
                ActivityCompat.requestPermissions(this, arrayOf(permission), REQUEST_SEND_SMS)
            } else {
                scheduleWork()
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            REQUEST_SEND_SMS -> {
                // If request is cancelled, the result arrays are empty.
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    scheduleWork()
                }
                return
            }
        }
    }

    override fun onStop() {
        // A service can be "started" and/or "bound". In this case, it's "started" by this Activity
        // and "bound" to the JobScheduler (also called "Scheduled" by the JobScheduler). This call
        // to stopService() won't prevent scheduled jobs to be processed. However, failing
        // to call stopService() would keep it alive indefinitely.
        stopService(Intent(this, MyJobService::class.java))
        super.onStop()
    }

    override fun onStart() {
        super.onStart()
        // Start service and provide it a way to communicate with this class.
        val startServiceIntent = Intent(this, MyJobService::class.java)
        val messengerIncoming = Messenger(handler)
        startServiceIntent.putExtra(MESSENGER_INTENT_KEY, messengerIncoming)
        startService(startServiceIntent)
    }


    class SendSmsWorker : Worker() {

        override fun doWork(): Result {

            try {
                Log.i(TAG, "doWork start")
                val duration = inputData.getLong(WORK_DURATION_KEY, 0L)
                Log.i(TAG, "doWork duration=$duration")
                TimeUnit.SECONDS.sleep(duration)

                if (MessengerHolder.messenger.isPresent) {
                    val message = Message.obtain()
                    message.what = MSG_COLOR_START
                    MessengerHolder.messenger.get().send(message)
                }

                val msg = inputData.getString(WORK_MESSAGE) ?: "?"
                sendSms(msg)

                Log.i(TAG, "doWork end")
            } catch (e: RemoteException) {
                Log.e(TAG, "Error passing service object back to activity.")
                return Result.FAILURE
            }

            return Result.SUCCESS
        }

        private fun sendSms(msg: String) {
            val cal = Calendar.getInstance()
            val fullMsg = String.format("Message from WorkManagerExample%n%tT%n%s", cal, msg)
            SmsManager.getDefault()
                    .sendTextMessage(TO, null, fullMsg, null, null)
        }

        companion object {
            private val TAG = "wh.SendSmsWorker"
        }
    }

    /**
     * One limitation of the WorkManager is that the worker classes must have
     * a zero argument constructor. They are instantiated by the WorkManager
     * and the Data object they can be passed can only contain a limited
     * amount of primitive data types.
     */
    object MessengerHolder {
        var messenger = Optional.empty<Messenger>()
    }


    /**
     * Execute some work using the WorkManager instead of the JobScheduler.
     */
    private fun scheduleWork() {

        if (!MessengerHolder.messenger.isPresent) {
            MessengerHolder.messenger = Optional.of(Messenger(handler))
        }

        val workRequest = OneTimeWorkRequestBuilder<SendSmsWorker>()

        val delay = delayEditText.text.toString()
        if (delay.isNotEmpty()) {
            workRequest.setInitialDelay(delay.toLong(), TimeUnit.SECONDS)
        }

        val constraints = Constraints.Builder()
        if (wiFiConnectivityRadioButton.isChecked) {
            constraints.setRequiredNetworkType(NetworkType.UNMETERED)
        } else if (anyConnectivityRadioButton.isChecked) {
            constraints.setRequiredNetworkType(NetworkType.CONNECTED)
        } else {
            constraints.setRequiredNetworkType(NetworkType.NOT_REQUIRED)
        }
        constraints.setRequiresDeviceIdle(requiresIdleCheckbox.isChecked)
        constraints.setRequiresCharging(requiresChargingCheckBox.isChecked)
        workRequest.setConstraints(constraints.build())

        var workDuration = durationTimeEditText.text.toString()
        if (workDuration.isEmpty()) workDuration = "1"
        val inputData = Data.Builder()
        inputData.putLong(WORK_DURATION_KEY, workDuration.toLong())

        val now = Calendar.getInstance()
        val then = Calendar.getInstance()
        then.add(Calendar.SECOND, delay.toInt() + workDuration.toInt())
        val msg1 = String.format("Job queued at %tT to execute at %tT", now, then)
        inputData.putString(WORK_MESSAGE, msg1)

        workRequest.setInputData(inputData.build())

        val msg = String.format("Job scheduled to execute at %tT", then)
        SmsManager.getDefault()
                .sendTextMessage(TO, null, msg, null, null)

        Log.d(TAG, "Scheduling work")
        val request = workRequest.build()
        jobId = Optional.of(request.id)
        WorkManager.getInstance().enqueue(request)
    }

    /**
     * Executed when user clicks on CANCEL ALL.
     */
    private fun cancelAllWork() {
        WorkManager.getInstance().cancelAllWork()
        showToast(getString(R.string.all_jobs_cancelled))
    }

    /**
     * Executed when user clicks on FINISH LAST TASK.
     */
    private fun finishJob() {
        if (jobId.isPresent) {
            WorkManager.getInstance().cancelWorkById(jobId.get())
            showToast(getString(R.string.cancelled_job, jobId.get()))
        } else {
            showToast(getString(R.string.no_jobs_to_cancel))
        }
    }

    companion object {
        private val TAG = "wh.MainActivity"
    }
}
