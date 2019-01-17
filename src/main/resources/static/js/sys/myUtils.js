$(function() {

  // section-1-1
  // 焦点离开时触发事件
  $('#init_value').change(function(){
    $.ajax({
      type: 'POST',
      url: 'utilsController/toggleCase',
      data: {initValue:$(this).val()},
      success: function(data){
        $('#value_result').text(data)
      },
      error:function(e){
        console.log(e);
      }
    });
  });

  // section-1-2
  getDateInfo();

  $('#myAffix').affix({
    offset: {
      top: 100, bottom: function () {
        return (this.bottom = $('.bs-footer').outerHeight(true))
      }
    }
  });

  // section-1-3
  $('#cal_number').change(function(){
    setCalDate();
  });
  $('#cal_type').change(function(){
    setCalDate();
  });

  // section-1-4
  // RGB颜色值改变时，调用RGB转十六进制数方法
  $('#rgb0').change(function(){
    setHexColor();
  });
  $('#rgb1').change(function(){
    setHexColor();
  });
  $('#rgb2').change(function(){
    setHexColor();
  });
  // 十六进制数颜色值改变时，调用十六进制数转RGB方法
  $('#hex_show').change(function(){
    setRgbColor();
  });

  // section-1-5
  $('#two_binary').change(function(){
    binaryChange(2);
  });
  $('#eight_binary').change(function(){
    binaryChange(8);
  });
  $('#ten_binary').change(function(){
    binaryChange(10);
  });
  $('#sixteen_binary').change(function(){
    binaryChange(16);
  });

  // section-2-1
  // 根据request获得用户信息，包括IP地址、MAC地址等信息
  $.ajax({
    type: "POST",
    url: "utilsController/getClientInfo",
    data: {initValue:$(this).val()},
    dataType: "json",
    success: function(result){
      bindControlInfo("client_info", result.data);
    },
    error:function(e){
      console.log(e);
    }
  });

  // section-3-1
  $('#match_text').change(function(){
    matchTextByReg();
  });
  $('#reg_text').change(function(){
    matchTextByReg();
  });
  $('#ignore_upper_or_lower').change(function(){
    matchTextByReg();
  });

  // section-3-2
  $('#json_input').change(function(){
    var json = JSON.parse($('#json_input').val());
    $('#json_result').JSONView(json);

    $('#json-collapsed').JSONView(json, {collapsed: true, nl2br: true});

  });

  $('#colorpicker').farbtastic('#color');

  initFileInput("picture_add_water_mark");
});

// 根据开始时间和结束时间，计算相隔天数
getDateInfo = function() {
  // 开始时间
  var dateStart = new Date($('#start_date').val());
  // 结束时间
  var dateEnd = new Date($('#end_date').val());
  // 时间差的毫秒数
  var resultDate = dateEnd.getTime() - dateStart.getTime();

  // 计算出相差天数
  $('#date_result').text(Math.floor(resultDate/(24*3600*1000) + 1))
}

// 根据开始日期加/减天数获得加/减的结果
setCalDate = function() {
  if ($('#cal_number') != undefined && $('#cal_number').val() != null) {
    //开始时间
    var dateFrom = new Date($('#from_date').val());
    //判断加减符号对日期进行加减
    if ($('#cal_type').val() == '0') {
      $('#date_cal_result').text(new Date(dateFrom.setDate(dateFrom.getDate() + Number($('#cal_number').val()))).format('yyyy-MM-dd'))
    } else {
      $('#date_cal_result').text(new Date(dateFrom.setDate(dateFrom.getDate() - Number($('#cal_number').val()))).format('yyyy-MM-dd'))
    }
  }
}

// RGB颜色转十六进制数
setHexColor = function() {
  // 清空错误信息
  $('#rgb_hex_message').text('');
  // 判断RGB数是否符合要求
  var pattern =new RegExp(/^(([0-9]|([1-9]\d)|(1\d\d)|(2([0-4]\d|5[0-5]))))$/);
  if(!pattern.test($('#rgb0').val()) ||
      !pattern.test($('#rgb1').val()) ||
      !pattern.test($('#rgb2').val())){
    $('#rgb_hex_message').text('RGB范围为0-255');
    return;
  }
  // 获得RGB数
  var r = parseInt($('#rgb0').val());
  var g = parseInt($('#rgb1').val());
  var b = parseInt($('#rgb2').val());

  // RGB数转十六进制数
  var hex = "#" + ((1 << 24) + (r << 16) + (g << 8) + b).toString(16).slice(1);
  // 页面上显示颜色
  $('#show_color').css("background-color", hex);
  $('#hex_show').val(hex);
}

// 十六进制数转RGB颜色
setRgbColor = function() {
  // 清空错误信息
  $('#rgb_hex_message').text('');
  // 获得十六进制数
  var hex_str = $('#hex_show').val();

  // 判断十六进制数是否以#开头，不以#开头加#
  if(!hex_str.startsWith("#")){
    hex_str = "#" + hex_str;
  }

  // 判断十六进制数是否符合要求
  var pattern =new RegExp(/^#[0-9a-fA-F]{6}$/);
  if(!pattern.test(hex_str)){
    $('#rgb_hex_message').text("16进制数输入错误");
    return;
  }
  // 页面上显示颜色
  $('#show_color').css("background-color", hex_str);
  // 十六进制数转RGB数
  var num = parseInt(hex_str.slice(1),16);
  var b = $('#rgb2').val(num %　256);
  num = parseInt(num / 256);
  var g = $('#rgb1').val(num %　256);
  num = parseInt(num / 256);
  var r = $('#rgb0').val(num %　256);
}

// 正则表达式测试工具
matchTextByReg = function() {

  // 判断输入内容是否为空
  if($('#reg_text') != undefined && $('#reg_text').val() != null &&
      $('#reg_text').val() != '' && $('#match_text').val() != '' &&
      $('#match_text') != undefined && $('#match_text').val() != null){

    // 获得页面输入的正则表达式
    // /i ： 忽略大小写
    // /g ：全文查找出现的所有匹配字符
    // /m：多行查找
    // /gi：全文查找、忽略大小写
    // /ig：全文查找、忽略大小写
    var reg = new RegExp($('#reg_text').val().replace("\\","\\\\"), "g");

    // 是否忽略大小写
    if($('#ignore_upper_or_lower:checked').length > 0){
      reg = new RegExp($('#reg_text').val().replace("\\","\\\\"), "ig");
    }
    // 匹配正则表达式并显示结果
    var found = $('#match_text').val().match(reg);
    $('#match_result').val(found);
  }
}

// 进制转换
binaryChange = function(binaryFlag) {
  if (binaryFlag == 2) {
    // 二进制转十进制
    $('#ten_binary').val(parseInt($('#two_binary').val(), 2));
    // 十进制转换为二、八、十六进制数
    tenToOtherBinaryChange();
  } else if (binaryFlag == 8) {
    // 八进制转十进制
    $('#ten_binary').val(parseInt($('#eight_binary').val(), 8));
    // 十进制转换为二、八、十六进制数
    tenToOtherBinaryChange();
  } else if (binaryFlag == 16) {
    // 十六进制转十进制
    $('#ten_binary').val(parseInt($('#sixteen_binary').val(), 16));
    // 十进制转换为二、八、十六进制数
    tenToOtherBinaryChange();
  } else if (binaryFlag == 10) {
    // 十进制转换为二、八、十六进制数
    tenToOtherBinaryChange();
  }
}

tenToOtherBinaryChange = function() {
  $('#two_binary').val(tenBinaryChangeTo($('#ten_binary').val(), 2));
  $('#eight_binary').val(tenBinaryChangeTo($('#ten_binary').val(), 8));
  $('#sixteen_binary').val(tenBinaryChangeTo($('#ten_binary').val(), 16));
}

initFileInput = function(ctrlName) {
  $('#' + ctrlName).fileinput({
    language: 'zh', //设置语言
    uploadUrl: "fileController/addWaterMark", //上传的地址
    allowedFileExtensions: ['jpg', 'gif', 'png'],//接收的文件后缀
    //uploadExtraData:{"id": 1, "fileName":'123.mp3'},
    uploadAsync: true, //默认异步上传
    showUpload: true, //是否显示上传按钮
    showRemove : true, //显示移除按钮
    showPreview : false, //是否显示预览
    showCaption: false,//是否显示标题
    browseClass: "btn btn-primary", //按钮样式
    dropZoneEnabled: false,//是否显示拖拽区域
    //minImageWidth: 50, //图片的最小宽度
    //minImageHeight: 50,//图片的最小高度
    //maxImageWidth: 1000,//图片的最大宽度
    //maxImageHeight: 1000,//图片的最大高度
    //maxFileSize: 0,//单位为kb，如果为0表示不限制文件大小
    minFileCount: 1,
    maxFileCount: 1, //表示允许同时上传的最大文件个数
    enctype: 'multipart/form-data',
    msgPlaceholder: "选择一个文件上传 ",
    validateInitialCount:true,
    previewFileIcon: "<i class='glyphicon glyphicon-king'></i>",
    msgFilesTooMany: "选择上传的文件数量({n}) 超过允许的最大数值{m}！",
  }).on("fileuploaded", function(event, data) {
    alert(data.response.msg);
    $(event.target).fileinput('clear').fileinput('unlock')
    $(event.target).parent().siblings('.fileinput-remove').hide()

    //$("#img_result").attr("src", "data:image/jpeg;base64," + );
  }).on('fileerror', function(event, data, msg) {  //一个文件上传失败
    alert('文件上传失败！'+msg);
  });
}
