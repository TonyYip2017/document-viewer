package org.emdev.ui.actions;

public interface IActionParameter {

    /**
     * @return parameter name
     */
    String getName();

    /**
     * Calculates a parameter value
     * 
     * @return value
     */
    Object getValue();
}
