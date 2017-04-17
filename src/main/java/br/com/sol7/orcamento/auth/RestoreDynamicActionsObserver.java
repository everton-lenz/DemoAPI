package br.com.sol7.orcamento.auth;


import br.com.sol7.orcamento.util.JSFUtil;

import javax.faces.context.ExternalContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.faces.render.ResponseStateManager;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.sun.faces.RIConstants;
import com.sun.faces.renderkit.RenderKitUtils;
import com.sun.faces.util.ComponentStruct;




public class RestoreDynamicActionsObserver implements PhaseListener {

    private static final long serialVersionUID = 1L;

    public static final String DYNAMIC_ACTIONS_RENDERKIT = "HTML_BASIC";

    @Override
    public PhaseId getPhaseId() {
        return PhaseId.RESTORE_VIEW;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void beforePhase(PhaseEvent event) {

        ResponseStateManager rsm = RenderKitUtils.getResponseStateManager(event.getFacesContext(), DYNAMIC_ACTIONS_RENDERKIT);

        ExternalContext extContext = JSFUtil.getExternalContext();

        Object[] rawState = (Object[]) rsm.getState(event.getFacesContext(), extContext.getRequestContextPath().replaceFirst(extContext.getRequestContextPath(), "").split("\\?")[0]);
        if (rawState == null) {
            return;
        }

        Map<String, Object> state = (Map<String,Object>) rawState[1];

        if(state == null){
            return;
        }

        List<Object> savedActions = (List<Object>) state.get(RIConstants.DYNAMIC_ACTIONS);

        if(savedActions == null){
            return;
        }

        for (Iterator<Object> iterator = savedActions.iterator(); iterator.hasNext();) {
            Object object = iterator.next();
            ComponentStruct action = new ComponentStruct();
            action.restoreState(event.getFacesContext(), object);

            //Esses são os únicos itens que usam Action Expression, e portanto, não devem ser mexidos
            if(ComponentStruct.ADD.equals(action.action) && (action.clientId.contains("itemLogOff"))){
                continue;
            }

            iterator.remove();
        }

    }

    @Override
    public void afterPhase(PhaseEvent event) {
        //
    }
}
