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

import android.app.Activity
import android.app.job.JobScheduler
import android.content.ComponentName
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.os.*
import android.util.Log
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.RadioButton
import androidx.work.*
import java.util.*
import java.util.concurrent.TimeUnit

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
        findViewById<Button>(R.id.schedule_button).setOnClickListener { scheduleWork() }
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

    class ExampleWorker : Worker() {

        private val TAG = this.javaClass.simpleName

        override fun doWork(): Result {

            try {
                Log.i(TAG, "doWork start")
                val duration = inputData.getLong(WORK_DURATION_KEY, 0L)
                Log.i(TAG, "doWork duration=$duration")
                TimeUnit.SECONDS.sleep(duration)

                val message = Message.obtain()
                message.what = MSG_COLOR_START
                MessengerHolder.messenger.get().send(message)
                Log.i(TAG, "doWork end")
            } catch (e: RemoteException) {
                Log.e(TAG, "Error passing service object back to activity.")
                return Result.FAILURE
            }

            return Result.SUCCESS
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

    private val WORK_TAG = "Workorama"

    /**
     * Execute some work using the WorkManager instead of the JobScheduler.
     */
    private fun scheduleWork() {

        if (!MessengerHolder.messenger.isPresent) {
            MessengerHolder.messenger = Optional.of(Messenger(handler))
        }

        val workRequest = OneTimeWorkRequestBuilder<ExampleWorker>()
                .addTag(WORK_TAG)

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

        val inputData = Data.Builder()
        var workDuration = durationTimeEditText.text.toString()
        if (workDuration.isEmpty()) workDuration = "1"
        inputData.putLong(WORK_DURATION_KEY, workDuration.toLong())
        workRequest.setInputData(inputData.build())

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
        private val TAG = "MainActivity"
    }
}
