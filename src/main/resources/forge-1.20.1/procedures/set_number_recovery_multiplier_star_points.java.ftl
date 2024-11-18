{
double _setval = ${input$variableValue};
${input$entity}.getCapability(PowerModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
capability.power_recovery_multiplier = _setval;
capability.syncPlayerVariables(${input$entity});
});
}