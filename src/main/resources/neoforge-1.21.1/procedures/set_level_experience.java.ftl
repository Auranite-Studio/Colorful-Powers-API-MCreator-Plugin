{
PowerModVariables.PlayerVariables _vars = ${input$entity}.getData(PowerModVariables.PLAYER_VARIABLES);
_vars.level_exp = ${input$variableValue};
_vars.syncPlayerVariables(${input$entity});
}
