package mvc.view;

/**
 * 接口描述:
 * 作者: ly
 * 创建时间:15:56 2019/1/13
 **/
public interface ViewResolver {
    View resolverView(String viewName);
}
