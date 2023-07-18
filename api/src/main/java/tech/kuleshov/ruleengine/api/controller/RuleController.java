package tech.kuleshov.ruleengine.api.controller;

import java.util.List;

import jakarta.validation.Valid;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import lombok.extern.slf4j.Slf4j;
import org.jboss.resteasy.reactive.RestPath;
import tech.kuleshov.ruleengine.api.exception.NotFoundException;
import tech.kuleshov.ruleengine.api.service.RuleRetrieveService;
import tech.kuleshov.ruleengine.api.service.RuleUpdateService;
import tech.kuleshov.ruleengine.base.RuleDefinition;

@Slf4j
@Path(value = "/rule")
public class RuleController {

    private final RuleUpdateService ruleUpdateService;
    private final RuleRetrieveService ruleRetrieveService;

    public RuleController(
            RuleUpdateService ruleUpdateService, RuleRetrieveService ruleRetrieveService) {
        this.ruleUpdateService = ruleUpdateService;
        this.ruleRetrieveService = ruleRetrieveService;
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{workflowId}")
    public void submitRule(@Valid RuleDefinition rule) {
        log.info("rule updated: {}/{}", rule.getWorkflowId(), rule.getId());
        ruleUpdateService.updateRule(rule);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{workflowId}")
    public List<RuleDefinition> listRoles(@RestPath String workflowId) {
        return ruleRetrieveService.listRulesByWorkflowId(workflowId);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{workflowId}/{ruleId}")
    public RuleDefinition getRole(@RestPath String workflowId, @RestPath String ruleId) {
        return ruleRetrieveService
                .findRuleDefinitionById(workflowId, ruleId)
                .orElseThrow(NotFoundException::new);
    }
}
