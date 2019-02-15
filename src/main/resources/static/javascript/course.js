$(function(){

    /**
     *  删除课程
     */
    var courseId = $('.del-courseId').val()
    alert(courseId)
    $(".delete-course-submit").click(function(){
        $.ajax({
            url: '/course/' + courseId,
            type: 'post',
            success: function (data) {
                $('.modal').modal('hide')
            },
            error: function (data) {
                $('.modal').modal('hide')
            }
        })
    })

})