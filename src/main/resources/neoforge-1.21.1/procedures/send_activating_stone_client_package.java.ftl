{
PowerModVariables.PlayerVariables _vars = ${input$entity}.getData(PowerModVariables.PLAYER_VARIABLES);
_vars.send_client_package = true;
_vars.syncPlayerVariables(${input$entity});
}