{
String _setval = ${input$variableValue};
${input$entity}.getCapability(PowerModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
capability.${field$element_number} = _setval;
capability.syncPlayerVariables(${input$entity});
});
}