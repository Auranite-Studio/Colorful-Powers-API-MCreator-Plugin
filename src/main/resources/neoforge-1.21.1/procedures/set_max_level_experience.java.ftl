{
PowerModVariables.PlayerVariables _vars = ${input$entity}.getData(PowerModVariables.PLAYER_VARIABLES);
_vars.max_level_exp = ${input$variableValue};
_vars.markSyncDirty();
}
