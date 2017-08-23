(function () {
    var $content = $('#content');
    var $usersTable = $('#users-table');
    var $userForm = $('#user-form');
    $userForm.validate();

    var fieldsArr = [
        {
            field: 'id',
            title: '编号',
            visible: false
        }, {
            field: 'nickname',
            title: '用户名'
        }, {
            field: 'username',
            title: '账号'
        }, {
            field: 'lastLogin',
            sortable: true,
            title: '最后登录时间'
        }, {
            field: 'registerTime',
            sortable: true,
            title: '注册时间'
        }, {
            title: '操作',
            field: 'realName',
            width: '200',
            formatter: function (value, row) {
                var pName = "冻结";
                var state = true;
                var btnType = "btn-danger";
                if (row.freeze) {
                    pName = "恢复";
                    state = false;
                    btnType = "btn-warning";
                }
                var p = '<a class="freeze-link tb-opt-btn btn btn-xs ' + btnType + '" data-id="' + row.id + '" data-state="' + state + '" href="#" data-name="' + pName + '">' + pName + '</a>';
                var e = '<a class="reset-link tb-opt-btn btn btn-info btn-xs" data-id="' + row.id + '">重置密码</a>';
                return p + e;
            }
        }
    ];

    var tableInit = function () {
        $usersTable.bootstrapTable('destroy');
        $usersTable.bootstrapTable({
            url: '/users',
            method: 'GET',
            dataType: 'json',
            striped: true,
            pageList: [15, 45, 100],
            pageSize: 15,
            pagination: true,
            sidePagination: "server",
            toolbar: "#toolbar",
            showRefresh: true,
            search: true,
            /**
             * 设置为undefined可以获取pageNumber，pageSize，searchText，sortName，sortOrder
             * 设置为limit可以获取limit, offset, search, sort, order
             * */
            queryParamsType: "undefined",
            queryParams: function (params) {
                return {
                    page: params.pageNumber - 1,   //页码
                    size: params.pageSize,  //页面大小
                    sort: params.sortName ? params.sortName + "," + params.sortOrder : '',
                    search: params.searchText ? params.searchText : ''
                };
            },
            //  对后台返回的数据进行处理：需返回符合bootstrap需要的数据格式
            responseHandler: function (res) {
                return {
                    total: res.data.total,
                    rows: res.data.data
                };
            },
            uniqueId: 'id',
            columns: fieldsArr
        })
    };

    var eventInit = function () {
        $('#add-user-submit').on('click', function (e) {
            e.preventDefault();

            $('.hint').hide();

            if (!$userForm.valid()) {
                return false;
            }

            var username = $('#username').val();
            var nickname = $('#nickname').val();
            $.ajax({
                url: 'users',
                method: 'POST',
                data: {
                    username: username,
                    nickname: nickname
                }
            }).done(function (data) {
                console.debug(data);
                if (data.success) {
                    $('#add-user-modal').modal('hide');
                    $usersTable.bootstrapTable('refresh');
                    //  清空输入框
                    $('#username').val('');
                    $('#nickname').val('');
                    layer.msg('添加成功');
                } else {
                    layer.msg(data.msg);
                }
            }).fail(function () {
                alert('请求失败');
            })
        });
        $content.on('click', '.freeze-link', function (e) {
            e.preventDefault();

            var $this = $(this);
            layer.confirm('确认' + $this.attr("data-name") + '该用户？', {icon: 3, title: '提示'}, function (index) {
                $.get('users/' + $this.attr('data-id') + '/freeze/' + $this.attr('data-state'), null, function (data) {
                    layer.close(index);
                    $usersTable.bootstrapTable('refresh');
                    data.success ? layer.msg('已' + $this.attr("data-name")) : layer.msg(data.msg);
                }, 'json');
            });
        });
        $content.on('click', '.reset-link', function (e) {
            e.preventDefault();

            var $this = $(this);
            layer.confirm('确认重置该用户密码？', {icon: 3, title: '提示'}, function (index) {
                $.get('users/' + $this.attr('data-id') + '/reset', null, function (data) {
                    layer.close(index);
                    data.success ? layer.msg('已重置') : layer.msg(data.msg);
                }, 'json');
            });
        });
    };
    tableInit();
    eventInit();
})();