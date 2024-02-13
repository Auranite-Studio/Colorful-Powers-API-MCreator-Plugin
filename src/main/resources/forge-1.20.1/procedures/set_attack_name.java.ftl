{
String _setval = ${input$variableValue};
${input$entity}.getCapability(PowerModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
capability.attack = _setval;
capability.syncPlayerVariables(${input$entity});
});
}