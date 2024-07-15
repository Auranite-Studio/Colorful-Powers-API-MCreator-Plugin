{
PowerModVariables.PlayerVariables _vars = ${input$entity}.getData(PowerModVariables.PLAYER_VARIABLES);
_vars.attack = ${input$variableValue};
_vars.syncPlayerVariables(${input$entity});
}