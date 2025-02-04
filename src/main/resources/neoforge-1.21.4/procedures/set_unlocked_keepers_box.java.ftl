{
PowerModVariables.PlayerVariables _vars = ${input$entity}.getData(PowerModVariables.PLAYER_VARIABLES);
_vars.unlock_keepers_box = ${input$variableValue};
_vars.syncPlayerVariables(${input$entity});
}