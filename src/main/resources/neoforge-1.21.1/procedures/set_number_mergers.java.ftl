{
PowerModVariables.PlayerVariables _vars = ${input$entity}.getData(PowerModVariables.PLAYER_VARIABLES);
_vars.mergers = ${input$variableValue};
_vars.markSyncDirty();
}