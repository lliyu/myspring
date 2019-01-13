package mvc.view;

/**
 * @author ly
 * @create 2019-01-13 16:09
 **/
public class InternalResourceViewResolver extends UrlBaseViewResolver{

    private String perfix;
    private String suffix;

    public String getPerfix() {
        return perfix;
    }

    public void setPerfix(String perfix) {
        this.perfix = perfix;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    @Override
    public View resolverView(String viewName) {
        return null;
    }
}
