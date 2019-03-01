var value_to_clj;

var array_to_clj = function (a) {
    return '[' + a.map(value_to_clj).join(' ') + ']';
};

var object_to_clj = function (obj) {
    var keys = Object.keys(obj);
    var kv_pairs = [];
    var k;

    for (var i = 0; i < keys.length; i++) {
        k = keys[i];
        kv_pairs.push(':' + k + ' ' + value_to_clj(obj[k]));
    }

    return '{' + kv_pairs.join(' ') + '}';
};

value_to_clj = function (v) {
    var result = '';

    switch (typeof v) {
        case 'string':
            result = '"' + v.replace(/"/g, '\\"') + '"';
            break;
        case 'undefined':
            result = 'nil';
            break;
        case 'number':
        case 'boolean':
            result = v.toString();
            break;
        case 'object':
            if (v === null) {
                result = 'nil';
            } else if (v instanceof Array) {
                result = array_to_clj(v);
            } else {
                result = object_to_clj(v);
            }
            break;
        default:
            throw "Unexpected data type.";
    }

    return result;
};

var json_to_clj = function (s) {
    var obj = JSON.parse(s);

    return value_to_clj(obj);
};
export default json_to_clj
