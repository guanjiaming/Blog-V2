interface attributesType {
  expires?: any;
  path?: string;
}

class CookieUtils {
  /**
   * 获取Cookie
   * @param name {string}
   */
  static getCookie(name: string): string | undefined {
    if (typeof document === 'undefined') {
      return;
    }
    let arr = document.cookie.match(new RegExp('(^| )' + name + '=([^;]*)(;|$)'));
    return arr == null ? undefined : unescape(arr[2]);
  }

  /**
   * 设置Cookie
   * @param name  {string}
   * @param value {string}
   * @param attributes {object} expires、path、domain 过期时间，单位：天
   */
  static setCookie(name: string, value: string, attributes: attributesType = {}): void | string {
    if (typeof document === 'undefined') {
      return;
    }

    if (typeof attributes.expires === 'number') {
      // @ts-ignore
      attributes.expires = new Date(new Date() * 1 + attributes.expires * 864e+5);
    }

    if(!attributes.path) {
      attributes.path = '/'
    }
    attributes.expires = attributes.expires ? attributes.expires.toUTCString() : '';

    let stringAttributes = '';
    for (const attributeName in attributes) {
      // @ts-ignore
      if (!attributes[attributeName]) {
        continue;
      }
      stringAttributes += '; ' + attributeName;
      // @ts-ignore
      if (attributes[attributeName] === true) {
        continue;
      }
      // @ts-ignore
      stringAttributes += '=' + attributes[attributeName].split(';')[0];
    }

    console.log("setCookieValue: -- ", name + '=' + value + stringAttributes);

    return (document.cookie = name + '=' + value + stringAttributes);
  }

  /**
   *  移除Cookie
   * @param name {string}
   */
  static removeCookie(name: string): void {
    this.setCookie(name, '', {expires: -1});
  }
}

export default CookieUtils;
