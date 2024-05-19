{
boolean _setval = ${input$variableValue};
${input$entity}.getCapability(PowerModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
capability.second_wheel_open_var = _setval;
capability.syncPlayerVariables(${input$entity});
});
}