package com.redmany.ram.model;

import java.io.Serializable;

/**
 * Created by hy on 2017/6/9.
 * Menu[菜单] 表 实体类
 */
public class Menu implements Serializable {
    private Integer Id;
    private String MenuName;
    private String FormName;
    private String ShowType;
    private String TemplateFrom;
    private Integer ParentMenu;
    private String Icon;
    private String Publish;
    private Integer State;
    private Integer index_number;
    private String MenuConds;
    private String IsTips;
    private String GoName;
    private String GoType;
    private String Param;
    private String NeedState;
    private String MenuType;
    private String confirm;
    private Integer panel;
    private Integer shortcut;
    private String pageicon;
    private String loadWay;
    private String transferParams;
    private String showPage;

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public String getMenuName() {
        return MenuName;
    }

    public void setMenuName(String menuName) {
        MenuName = menuName;
    }

    public String getFormName() {
        return FormName;
    }

    public void setFormName(String formName) {
        FormName = formName;
    }

    public String getShowType() {
        return ShowType;
    }

    public void setShowType(String showType) {
        ShowType = showType;
    }

    public String getTemplateFrom() {
        return TemplateFrom;
    }

    public void setTemplateFrom(String templateFrom) {
        TemplateFrom = templateFrom;
    }

    public Integer getParentMenu() {
        return ParentMenu;
    }

    public void setParentMenu(Integer parentMenu) {
        ParentMenu = parentMenu;
    }

    public String getIcon() {
        return Icon;
    }

    public void setIcon(String icon) {
        Icon = icon;
    }

    public String getPublish() {
        return Publish;
    }

    public void setPublish(String publish) {
        Publish = publish;
    }

    public Integer getState() {
        return State;
    }

    public void setState(Integer state) {
        State = state;
    }

    public Integer getIndex_number() {
        return index_number;
    }

    public void setIndex_number(Integer index_number) {
        this.index_number = index_number;
    }

    public String getMenuConds() {
        return MenuConds;
    }

    public void setMenuConds(String menuConds) {
        MenuConds = menuConds;
    }

    public String getIsTips() {
        return IsTips;
    }

    public void setIsTips(String isTips) {
        IsTips = isTips;
    }

    public String getGoName() {
        return GoName;
    }

    public void setGoName(String goName) {
        GoName = goName;
    }

    public String getGoType() {
        return GoType;
    }

    public void setGoType(String goType) {
        GoType = goType;
    }

    public String getParam() {
        return Param;
    }

    public void setParam(String param) {
        Param = param;
    }

    public String getNeedState() {
        return NeedState;
    }

    public void setNeedState(String needState) {
        NeedState = needState;
    }

    public String getMenuType() {
        return MenuType;
    }

    public void setMenuType(String menuType) {
        MenuType = menuType;
    }

    public String getConfirm() {
        return confirm;
    }

    public void setConfirm(String confirm) {
        this.confirm = confirm;
    }

    public Integer getPanel() {
        return panel;
    }

    public void setPanel(Integer panel) {
        this.panel = panel;
    }

    public Integer getShortcut() {
        return shortcut;
    }

    public void setShortcut(Integer shortcut) {
        this.shortcut = shortcut;
    }

    public String getPageicon() {
        return pageicon;
    }

    public void setPageicon(String pageicon) {
        this.pageicon = pageicon;
    }

    public String getLoadWay() {
        return loadWay;
    }

    public void setLoadWay(String loadWay) {
        this.loadWay = loadWay;
    }

    public String getTransferParams() {
        return transferParams;
    }

    public void setTransferParams(String transferParams) {
        this.transferParams = transferParams;
    }

    public String getShowPage() {
        return showPage;
    }

    public void setShowPage(String showPage) {
        this.showPage = showPage;
    }
}
