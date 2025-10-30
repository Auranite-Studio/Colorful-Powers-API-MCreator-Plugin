{
PowerModVariables.PlayerVariables _vars = ${input$entity}.getData(PowerModVariables.PLAYER_VARIABLES);
_vars.max_power = ${input$variableValue};
_vars.markSyncDirty();
}
