/**
 * This utility checks the status of all git repositories and reports if they are up to date wrt the remote origin.
 * 
 * @author Warwick Hunter
 * @since  2018-01-21
 */ 
package git

import org.eclipse.jgit.api.Git
import org.eclipse.jgit.api.ListBranchCommand
import org.eclipse.jgit.api.Status
import java.io.File

fun main(args : Array<String>) {
    val dirs = mutableListOf<File>()
    if (args.size == 0) {
        val homeDir = System.getenv("HOME")
        File("$homeDir/dev").walkTopDown().forEach { file ->
            if (file.isDirectory && hasGitSubDirectory(file)) {
                dirs.add(file)
            }
        }
    } else {
        args.forEach {
            dirs.add(File(it))
        }
    }
    dirs.forEach { gitDir ->
        println(gitDir)
        if (gitDir.exists() && gitDir.canRead()) {
            printRepoStatus(gitDir)
        } else {
            println("$gitDir does not exist")
        }
    }
}

fun hasGitSubDirectory(dir: File): Boolean {
    val gitSubDir = File(dir, ".git")
    return gitSubDir.exists() && gitSubDir.isDirectory
}

fun report(label: String, files: Set<String>) {
    if (files.isEmpty()) {
        return
    }
    if (files.size < 2) {
        print("    $label: ")
        files.forEach{ print("$it ")}
        println("")
    } else {
        println("    $label: ")
        files.forEach{ println("        $it")}
    }
}

fun reportBranches(command: ListBranchCommand) {
    print("    branches: ")
    command.call().forEach{
        val name = it.leaf.name.replaceFirst("refs/heads/", "")
        print("${name} ")
    }
    println("")
}

fun printRepoStatus(repoDir : File) {
    val gitApi:Git = Git.open(repoDir)
    val status: Status = gitApi.status().call()
    if (!status.isClean) {
        reportBranches(gitApi.branchList())
        report("added", status.added)
        report("changed", status.changed)
        report("conflicting", status.conflicting)
        report("missing", status.missing)
        report("modified", status.modified)
        report("removed", status.removed)
        report("untracked", status.untracked)
        if (status.hasUncommittedChanges()) {
            println("    There are uncommitted changes")
        }
        println("")
    }
}

