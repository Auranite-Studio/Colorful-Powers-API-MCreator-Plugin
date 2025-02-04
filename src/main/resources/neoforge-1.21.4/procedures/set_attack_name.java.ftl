{
PowerModVariables.PlayerVariables _vars = ${input$entity}.getData(PowerModVariables.PLAYER_VARIABLES);
_vars.ability = ${input$variableValue};
_vars.syncPlayerVariables(${input$entity});
}