package mvc.model;

import mvc.view.View;

import java.util.Map;

/**
 * @author ly
 * @create 2019-01-12 15:36
 **/
public class ModelAndView {
    private Map<String, Object> model;
    private Object view;
    private HttpStatus status;

    public ModelAndView() {
        super();
    }

    public ModelAndView(View view) {
        super();
        this.view = view;
    }

    public ModelAndView(Map<String, Object> model, View view) {
        super();
        this.model = model;
        this.view = view;
    }

    public ModelAndView(String viewName) {
        super();
        this.view = viewName;
    }

    public ModelAndView(Map<String, Object> model, String viewName) {
        super();
        this.model = model;
        this.view = viewName;
    }

    public ModelAndView(View view, HttpStatus status) {
        super();
        this.view = view;
        this.status = status;
    }

    public ModelAndView(String viewName, HttpStatus status) {
        super();
        this.view = viewName;
        this.status = status;
    }

    public ModelAndView(Map<String, Object> model, Object view, HttpStatus status) {
        super();
        this.model = model;
        this.view = view;
        this.status = status;
    }

    boolean hasView() {
        return this.view != null;
    }

    public String getViewName() {
        return (this.view instanceof String ? (String) this.view : null);
    }

    public Map<String, Object> getModel() {
        return model;
    }

    public void setModel(Map<String, Object> model) {
        this.model = model;
    }

    public View getView() {
        return (this.view instanceof View ? (View) this.view : null);
    }

    public void setView(View view) {
        this.view = view;
    }

    public void setViewName(String viewName) {
        this.view = viewName;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public void setAttrubite(String name, Object attr){
        this.model.put(name, attr);
    }

}
