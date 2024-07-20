{
boolean _setval = ${input$variableValue};
${input$entity}.getCapability(PowerModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
capability.check_activating_stone = _setval;
capability.syncPlayerVariables(${input$entity});
});
}