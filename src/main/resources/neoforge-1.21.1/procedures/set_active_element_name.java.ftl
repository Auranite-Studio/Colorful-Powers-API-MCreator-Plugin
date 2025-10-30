{
PowerModVariables.PlayerVariables _vars = ${input$entity}.getData(PowerModVariables.PLAYER_VARIABLES);
_vars.${field$element_number} = ${input$variableValue};
_vars.markSyncDirty();
}