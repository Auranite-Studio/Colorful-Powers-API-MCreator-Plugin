{
PowerModVariables.PlayerVariables _vars = ${input$entity}.getData(PowerModVariables.PLAYER_VARIABLES);
_vars.${field$boosters_slots} = ${input$variableValue};
_vars.syncPlayerVariables(${input$entity});
}