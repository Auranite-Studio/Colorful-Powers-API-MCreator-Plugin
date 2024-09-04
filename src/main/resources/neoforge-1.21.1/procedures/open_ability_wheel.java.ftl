{
PowerModVariables.PlayerVariables _vars = ${input$entity}.getData(PowerModVariables.PLAYER_VARIABLES);
_vars.${field$wheel_number} = ${input$variableValue};
_vars.syncPlayerVariables(${input$entity});
}