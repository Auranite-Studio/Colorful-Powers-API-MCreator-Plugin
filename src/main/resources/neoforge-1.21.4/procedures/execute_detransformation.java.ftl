{
PowerModVariables.PlayerVariables _vars = ${input$entity}.getData(PowerModVariables.PLAYER_VARIABLES);
_vars.detransf_key_var = true;
_vars.syncPlayerVariables(${input$entity});
}