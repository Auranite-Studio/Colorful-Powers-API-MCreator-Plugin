{
boolean _setval = ${input$variableValue};
${input$entity}.getCapability(PowerModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
capability.transfered_power = _setval;
capability.syncPlayerVariables(${input$entity});
});
}