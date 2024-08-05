{
PowerModVariables.PlayerVariables _vars = ${input$entity}.getData(PowerModVariables.PLAYER_VARIABLES);
_vars.power = ${input$variableValue};
_vars.syncPlayerVariables(${input$entity});
}
