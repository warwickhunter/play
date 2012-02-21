/**
 * Copyright Warwick Hunter 2012. All rights reserved.
 */
package org.computer.whunter.rpm.ant;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.Task;

/**
 * An Ant task that parses an RPM Spec file and pushes the information from the spec file
 * into the ant properties.
 * 
 * @author Warwick Hunter (w.hunter@computer.org)
 * @date   2012-02-21
 */
public class RpmSpecParser extends Task {
    
    String srcfile;
    
    public void setSrcfile(String file) {
        srcfile = file;
    }

    @Override
    public void execute() throws BuildException {
        Project project = getProject();
        log("project " + project.getProperty("ant.project.name"));
        log("parsing " + srcfile);
        project.setProperty("wasa", "was here");
        super.execute();
    }

}
