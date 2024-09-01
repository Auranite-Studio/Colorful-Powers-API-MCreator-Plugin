{
PowerModVariables.PlayerVariables _vars = ${input$entity}.getData(PowerModVariables.PLAYER_VARIABLES);
_vars.active_battery = ${input$variableValue};
_vars.syncPlayerVariables(${input$entity});
}