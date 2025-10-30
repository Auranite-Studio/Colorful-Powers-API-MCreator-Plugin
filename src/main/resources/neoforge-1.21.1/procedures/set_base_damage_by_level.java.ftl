{
PowerModVariables.PlayerVariables _vars = ${input$entity}.getData(PowerModVariables.PLAYER_VARIABLES);
_vars.base_damage_by_lvl = ${input$variableValue};
_vars.markSyncDirty();
}
