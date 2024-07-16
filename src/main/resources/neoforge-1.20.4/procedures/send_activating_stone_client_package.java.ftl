{
PowerModVariables.PlayerVariables _vars = ${input$entity}.getData(PowerModVariables.PLAYER_VARIABLES);
_vars.check_activating_stone = true;
_vars.syncPlayerVariables(${input$entity});
}