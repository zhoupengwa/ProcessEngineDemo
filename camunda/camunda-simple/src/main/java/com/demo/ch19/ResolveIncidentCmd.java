package com.demo.ch19;

import org.camunda.bpm.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.camunda.bpm.engine.impl.incident.IncidentContext;
import org.camunda.bpm.engine.impl.interceptor.Command;
import org.camunda.bpm.engine.impl.interceptor.CommandContext;
import org.camunda.bpm.engine.runtime.Incident;

/**
 * @author zhoupeng create on 2021-03-11
 */
public class ResolveIncidentCmd implements Command {

   private Incident incident;

    public ResolveIncidentCmd(Incident incident) {
        this.incident = incident;
    }

    @Override
    public Void execute(CommandContext commandContext) {
        IncidentContext incidentContext=new IncidentContext(incident);

        ProcessEngineConfigurationImpl processEngineConfiguration = commandContext.getProcessEngineConfiguration();
        processEngineConfiguration.getIncidentHandlers().get(incident.getIncidentType())
                .resolveIncident(incidentContext);

        return null;
    }
}
