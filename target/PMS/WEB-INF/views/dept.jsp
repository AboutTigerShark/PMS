<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>部门管理</title>
    <jsp:include page="/common/backend_common.jsp"/>
    <jsp:include page="/common/page.jsp"/>
</head>
<body class="no-skin" youdao="bind" style="background: white">
<input id="gritter-light" checked="" type="checkbox" class="ace ace-switch ace-switch-5"/>

<div class="page-header">
    <h1>
        用户管理
        <small>
            <i class="ace-icon fa fa-angle-double-right"></i>
            维护部门与用户关系
        </small>
    </h1>
</div>
<div class="main-content-inner">
    <div class="col-sm-3">
        <div class="table-header">
            部门列表&nbsp;&nbsp;
            <a class="green" href="#">
                <i class="ace-icon fa fa-plus-circle orange bigger-130 dept-add"></i>
            </a>
        </div>
        <div id="deptList1">
        </div>
    </div>
    <div class="col-sm-9">
        <div class="col-xs-12">
            <div class="table-header">
                用户列表&nbsp;&nbsp;
                <a class="green" href="#">
                    <i class="ace-icon fa fa-plus-circle orange bigger-130 user-add"></i>
                </a>
            </div>
            <div>
                <div id="dynamic-table_wrapper" class="dataTables_wrapper form-inline no-footer">
                    <div class="row">
                        <div class="col-xs-6">
                            <div class="dataTables_length" id="dynamic-table_length"><label>
                                展示
                                <select id="pageSize" name="dynamic-table_length" aria-controls="dynamic-table" class="form-control input-sm">
                                    <option value="10">10</option>
                                    <option value="25">25</option>
                                    <option value="50">50</option>
                                    <option value="100">100</option>
                                </select> 条记录 </label>
                            </div>
                        </div>
                    </div>
                    <table id="dynamic-table" class="table table-striped table-bordered table-hover dataTable no-footer" role="grid"
                           aria-describedby="dynamic-table_info" style="font-size:14px">
                        <thead>
                        <tr role="row">
                            <th tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1">
                                姓名
                            </th>
                            <th tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1">
                                所属部门
                            </th>
                            <th tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1">
                                邮箱
                            </th>
                            <th tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1">
                                电话
                            </th>
                            <th tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1">
                                状态
                            </th>
                            <th class="sorting_disabled" rowspan="1" colspan="1" aria-label=""></th>
                        </tr>
                        </thead>
                        <tbody id="userList"></tbody>
                    </table>
                    <div class="row" id="userPage">
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<div id="dialog-dept-form" style="display: none;">
    <form id="deptForm">
        <table class="table table-striped table-bordered table-hover dataTable no-footer" role="grid">
            <tr>
                <td style="width: 80px;"><label for="parentId">上级部门</label></td>
                <td>
                    <select id="parentId" name="parentId" data-placeholder="选择部门" style="width: 200px;"></select>
                    <input type="hidden" name="id" id="deptId"/>
                </td>
            </tr>
            <tr>
                <td><label for="deptName">名称</label></td>
                <td><input type="text" name="name" id="deptName" value="" class="text ui-widget-content ui-corner-all"></td>
            </tr>
            <tr>
                <td><label for="deptSeq">顺序</label></td>
                <td><input type="text" name="seq" id="deptSeq" value="1" class="text ui-widget-content ui-corner-all"></td>
            </tr>
            <tr>
                <td><label for="deptRemark">备注</label></td>
                <td><textarea name="remark" id="deptRemark" class="text ui-widget-content ui-corner-all" rows="3" cols="25"></textarea></td>
            </tr>
        </table>
    </form>
</div>
<div id="dialog-user-form" style="display: none;">
    <form id="userForm">
        <table class="table table-striped table-bordered table-hover dataTable no-footer" role="grid">
            <tr>
                <td style="width: 80px;"><label for="parentId">所在部门</label></td>
                <td>
                    <select id="deptSelectId" name="deptId" data-placeholder="选择部门" style="width: 200px;"></select>
                </td>
            </tr>
            <tr>
                <td><label for="userName">名称</label></td>
                <input type="hidden" name="id" id="userId"/>
                <td><input type="text" name="username" id="userName" value="" class="text ui-widget-content ui-corner-all"></td>
            </tr>
            <tr>
                <td><label for="userMail">邮箱</label></td>
                <td><input type="text" name="mail" id="userMail" value="" class="text ui-widget-content ui-corner-all"></td>
            </tr>
            <tr>
                <td><label for="userTelephone">电话</label></td>
                <td><input type="text" name="telephone" id="userTelephone" value="" class="text ui-widget-content ui-corner-all"></td>
            </tr>
            <tr>
                <td><label for="userStatus">状态</label></td>
                <td>
                    <select id="userStatus" name="status" data-placeholder="选择状态" style="width: 150px;">
                        <option value="1">有效</option>
                        <option value="0">无效</option>
                        <option value="2">删除</option>
                    </select>
                </td>
            </tr>
            <tr>
                <td><label for="userRemark">备注</label></td>
                <td><textarea name="remark" id="userRemark" class="text ui-widget-content ui-corner-all" rows="3" cols="25"></textarea></td>
            </tr>
        </table>
    </form>
</div>

<script id="deptListTemplate" type="x-tmpl-mustache">
<ol class="dd-list">
    {{#deptList}}
        <li class="dd-item dd2-item dept-name" id="dept_{{id}}" href="javascript:void(0)" data-id="{{id}}">
            <div class="dd2-content" style="cursor:pointer;">
            {{name}}
            <span style="float:right;">
                <a class="green dept-edit" href="#" data-id="{{id}}" >
                    <i class="ace-icon fa fa-pencil bigger-100"></i>
                </a>
                &nbsp;
                <a class="red dept-delete" href="#" data-id="{{id}}" data-name="{{name}}">
                    <i class="ace-icon fa fa-trash-o bigger-100"></i>
                </a>
            </span>
            </div>
        </li>
    {{/deptList}}
</ol>
</script>
<script id="userListTemplate" type="x-tmpl-mustache">
{{#userList}}
<tr role="row" class="user-name odd" data-id="{{id}}"><!--even -->
    <td><a href="#" class="user-edit" data-id="{{id}}">{{username}}</a></td>
    <td>{{showDeptName}}</td>
    <td>{{mail}}</td>
    <td>{{telephone}}</td>
    <td>{{#bold}}{{showStatus}}{{/bold}}</td> <!-- 此处套用函数对status做特殊处理 -->
    <td>
        <div class="hidden-sm hidden-xs action-buttons">
            <a class="green user-edit" href="#" data-id="{{id}}">
                <i class="ace-icon fa fa-pencil bigger-100"></i>
            </a>
            <a class="red user-acl" href="#" data-id="{{id}}">
                <i class="ace-icon fa fa-flag bigger-100"></i>
            </a>
        </div>
    </td>
</tr>
{{/userList}}
</script>
<script type="application/javascript">
    $(function () {
        var deptList; //缓存树形部门列表
        var deptMap = {}; //缓存map格式的部门信息
        var optionStr = ""; //选项中的数据
        var lastClickDeptId = -1; //最后一次点击的部门Id

        var deptListTemplate = $('#deptListTemplate').html();
        Mustache.parse(deptListTemplate);

        loadDeptTree();

        //加载部门树
        function loadDeptTree() {
            $.ajax({
                url: "/sys/dept/tree.json",
                success: function (result) {
                    if(result.ret){
                        deptList = result.data;
                        var rendered = Mustache.render(deptListTemplate, {deptList: result.data});
                        $('#deptList1').html(rendered);  //渲染到id为deptList1的标签上
                        recursiveRenderDept(result.data); //第一层递归
                        bindDeptClick();
                    }else {
                        showMessage("加载部门列表", result.msg, false);
                    }
                }
            })
        }
        //递归渲染部门列表
        function recursiveRenderDept(deptList) {
            if(deptList && deptList.length > 0){  //递归结束条件
                $(deptList).each(function (i, dept) {
                    deptMap[dept.id] = dept;
                    if(dept.deptList.length > 0){
                        var rendered = Mustache.render(deptListTemplate, {deptList: dept.deptList});
                        $('#dept_' + dept.id).append(rendered);
                        recursiveRenderDept(dept.deptList); //将dept的下一层进行递归
                    }
                })
            }
        }

        //绑定部门点击事件
        function bindDeptClick() {
            //1.点击部门编辑按钮
            $(".dept-edit").click(function (e) {
                e.preventDefault();
                e.stopPropagation();
                var deptId = $(this).attr("data-id"); //取出当前元素的部门Id
                // handleDeptSelected(deptId);
                $('#dialog-dept-form').dialog({
                    model: true,
                    title: "编辑部门",
                    open: function (event, ui) {
                        $(".ui-dialog-titlebar-close", $(this).parent()).hide(); //隐藏默认关闭的属性
                        optionStr = "<option value=\"0\">-</option>";
                        recursiveRenderDeptSelect(deptList, 1); //调用递归第一层
                        $("#deptForm")[0].reset(); //重置dialog表单
                        $("#parentId").html(optionStr);
                        $("#deptId").val(deptId);
                        var targetDept = deptMap[deptId];
                        if(targetDept){ //在弹出来的dialog中填充字段
                            $("#parentId").val(targetDept.parentId);
                            $("#deptName").val(targetDept.name);
                            $("#deptSeq").val(targetDept.seq);
                            $("#deptRemark").val(targetDept.remark);
                        }
                    },
                    buttons: {
                        "更新": function (e) {
                            e.preventDefault();
                            updateDept(false, function (data) {
                                $("#dialog-dept-form").dialog("close");
                            }, function (data) {
                                showMessage("更新部门", data.msg, false);
                            })
                        },
                        "取消": function () {
                            $("#dialog-dept-form").dialog("close");
                        }
                    }
                })
            });

            //2.点击部门删除按钮(需要验证有没有用户才能删除)
            $(".dept-delete").click(function (e) {
                e.preventDefault();
                e.stopPropagation();
                var deptId = $(this).attr("data-id");
                var deptName = $(this).attr("data-name");
                if(confirm("确定要删除部门["+ deptName + "]吗?")){
                    //TODO:
                    console.log("delete dept:" + deptName);
                }
            });

            $(".dept-name").click(function (e) {
                e.preventDefault();
                e.stopPropagation();
                var deptId = $(this).attr("data-id");
                handleDeptSelected(deptId);
            });
            //3.点击部门名称加载用户列表

        }

        //处理点击事件的函数?
        function handleDeptSelected(deptId){
            if(lastClickDeptId != -1){ //==改成!=...
                var lastDept = $("#dept_" + lastClickDeptId + " .dd2-content:first");
                lastDept.removeClass("btn-yellow");
                lastDept.removeClass("no-hover");
            }
            var currentDept = $("#dept_" + deptId + " .dd2-content:first");
            currentDept.addClass("btn-yellow");
            currentDept.addClass("no-hover");
            lastClickDeptId = deptId;
            loadUserList(deptId);
        }

        function loadUserList(deptId){
            //TODO:
            console.log("load userlist, deptId:" + deptId);
        }


        //部门添加的按钮点击事件
        $('.dept-add').click(function () {
            $('#dialog-dept-form').dialog({
                model: true,
                title: "新增部门",
                open: function (event, ui) {
                    $(".ui-dialog-titlebar-close", $(this).parent()).hide(); //默认关闭属性隐藏
                    optionStr = "<option value=\"0\">-</option>";
                    recursiveRenderDeptSelect(deptList, 1); //调用递归第一层
                    $("#deptForm")[0].reset();
                    $("#parentId").html(optionStr);
                },
                buttons: {
                    "添加": function (e) {
                        e.preventDefault();
                        updateDept(true, function (data) {
                            $("#dialog-dept-form").dialog("close");
                        }, function (data) {
                            showMessage("新增部门", data.msg, false);
                        })
                    },
                    "取消": function () {
                        $("#dialog-dept-form").dialog("close");
                    }
                }
            })
        });

        function recursiveRenderDeptSelect(deptList, level) {
            level = level | 0;
            if(deptList && deptList.length > 0){
                $(deptList).each(function (i, dept) {
                    deptMap[dept.id] = dept;
                    var blank = "";
                    if(level > 1){
                        for (var j = 3; j <= level; j++){
                            blank += "..";
                        }
                        blank += "∟";
                    }
                    optionStr += Mustache.render("<option value='{{id}}'>{{name}}</option>", {id: dept.id, name: blank + dept.name});
                    if(dept.deptList && dept.deptList.length > 0){
                        recursiveRenderDeptSelect(dept.deptList, level + 1);
                    }
                });
            }
        }

        function updateDept(isCreate, successCallback, failCallback) {
            $.ajax({
                url: isCreate ? "/sys/dept/save.json" : "/sys/dept/update.json",
                data: $("#deptForm").serializeArray(),
                type: 'POST',
                success: function (result) {
                    if(result.ret){
                        loadDeptTree();
                        if(successCallback){
                            successCallback(result);
                        }
                    }else {
                        if(failCallback){
                            failCallback(result);
                        }
                    }
                }
            })
        }

    });
</script>

</body>
</html>