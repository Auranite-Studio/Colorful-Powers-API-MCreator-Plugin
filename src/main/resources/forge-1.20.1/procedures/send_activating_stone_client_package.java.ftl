{
boolean _setval = ${input$variableValue};
${input$entity}.getCapability(PowerModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
capability.send_client_package = _setval;
capability.syncPlayerVariables(${input$entity});
});
}