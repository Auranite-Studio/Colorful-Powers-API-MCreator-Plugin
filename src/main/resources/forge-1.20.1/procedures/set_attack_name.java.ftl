{
String _setval = ${input$variableValue};
${input$entity}.getCapability(PowerModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
capability.ability = _setval;
capability.syncPlayerVariables(${input$entity});
});
}