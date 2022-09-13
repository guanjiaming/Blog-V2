/**
 * @description 时间格式化
 * @param {Number|Date} value 时间戳
 * @param {String} fmt 转化格式
 */

class DateUtils {
    static formatDate(value: string | Date, fmt = 'yyyy-MM-dd HH:mm:ss') {
        let valueDate = new Date(value);
        let o = {
            'M+': valueDate.getMonth() + 1,               //月份
            'd+': valueDate.getDate(),                    //日
            'H+': valueDate.getHours(),                   //小时
            'm+': valueDate.getMinutes(),                 //分
            's+': valueDate.getSeconds(),                 //秒
            'q+': Math.floor((valueDate.getMonth() + 3) / 3), //季度
            'S': valueDate.getMilliseconds()             //毫秒
        };
        if (/(y+)/.test(fmt))
            fmt = fmt.replace(RegExp.$1, (valueDate.getFullYear() + '').substr(4 - RegExp.$1.length));
        for (let k in o)
            if (new RegExp('(' + k + ')').test(fmt))
                // @ts-ignore
                fmt = fmt.replace(RegExp.$1, (RegExp.$1.length === 1) ? (o[k]) : (('00' + o[k]).substr(('' + o[k]).length)));
        return fmt;
    }
}

export default DateUtils;