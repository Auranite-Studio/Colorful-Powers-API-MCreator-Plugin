{
PowerModVariables.PlayerVariables _vars = ${input$entity}.getData(PowerModVariables.PLAYER_VARIABLES);
_vars.level = ${input$variableValue};
_vars.syncPlayerVariables(${input$entity});
}
