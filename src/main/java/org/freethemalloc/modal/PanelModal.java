/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.freethemalloc.modal;

/**
 *
 * @author Harshana
 */
public class PanelModal {

    private boolean isOpened;
    private boolean isClosed;
    private boolean isMinimized;
    
    public PanelModal(boolean isOpened, boolean isClosed, boolean isMinimized) {
        this.isOpened = isOpened;
        this.isClosed = isClosed;
        this.isMinimized = isMinimized;
    }
    /**
     * whether the panel is opened
     * @return boolean 
     */
    public boolean isIsOpened() {
        return isOpened;
    }

    
    public void setIsOpened(boolean isOpened) {
        this.isOpened = isOpened;
    }
    
    /**
     * whether the panel is closed
     * @return boolean 
     */
    public boolean isIsClosed() {
        return isClosed;
    }
    
    /**
     * Set the panel state to closed
     * @param isClosed 
     */
    public void setIsClosed(boolean isClosed) {
        this.isClosed = isClosed;
    }
    
    /**
     * whether the panel is minimized
     * @return boolean 
     */
    public boolean isIsMinimized() {
        return isMinimized;
    }

    /**
     * Set the panel state to minimize
     * @param isMinimized 
     */
    public void setIsMinimized(boolean isMinimized) {
        this.isMinimized = isMinimized;
    }
    
}
