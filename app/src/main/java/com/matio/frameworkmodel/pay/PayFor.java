package com.matio.frameworkmodel.pay;

import android.app.Activity;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;


public class PayFor {

    /**
     * 支付
     */
    public void pay(Activity activity ,String subject, String body , double total) {
//        第一步生成定点信息号
        String orderInfo = getOrderInfo(subject, body, String.valueOf(total));
//    第二步 对生成的订单信息号签名   签名工具由支付平台提供
        String signInfo = SignUtils.sign(orderInfo, PayConstant.RSA_PRIVATE);
        //第三步 签名后的订单信息转化成UrlCOde
        try {
            String urlOderInfo = URLEncoder.encode(signInfo, "UTF-8");
            /**
             * 第四步
             * 完整的符合支付宝参数规范的订单信息
             */
            final String payInfo = orderInfo + "&sign=\"" + urlOderInfo + "\"&" + getSignType();
            new PayAsyncTask(activity).execute(payInfo);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }


    /**
     * 第一步生下订单(生成订单信息) 服务器返回的
     * @return
     * create the order info. 创建订单信息
     */
    private String getOrderInfo(String subject, String body, String price) {

        // 签约合作者身份ID 2088
        String orderInfo = "partner=" + "\"" + PayConstant.PARTNER + "\"";

        // 签约卖家支付宝账号
        orderInfo += "&seller_id=" + "\"" + PayConstant.SELLER + "\"";

        // 商户网站唯一订单号
        orderInfo += "&out_trade_no=" + "\"" + getOutTradeNo() + "\"";
        // 商品名称
        orderInfo += "&subject=" + "\"" + subject + "\"";

        // 商品详情
        orderInfo += "&body=" + "\"" + body + "\"";

        // 商品金额
        orderInfo += "&total_fee=" + "\"" + price + "\"";

        // 服务器异步通知页面路径
        orderInfo += "&notify_url=" + "\"" + PayConstant.NOTIFY_URL + "\"";

        // 服务接口名称， 固定值
        orderInfo += "&service=\"mobile.securitypay.pay\"";

        // 支付类型， 固定值
        orderInfo += "&payment_type=\"1\"";

        // 参数编码， 固定值
        orderInfo += "&_input_charset=\"utf-8\"";

        // 设置未付款交易的超时时间
        // 默认30分钟，一旦超时，该笔交易就会自动被关闭。
        // 取值范围：1m～15d。
        // m-分钟，h-小时，d-天，1c-当天（无论交易何时创建，都在0点关闭）。
        // 该参数数值不接受小数点，如1.5h，可转换为90m。
        orderInfo += "&it_b_pay=\"1c\"";

        // extern_token为经过快登授权获取到的alipay_open_id,带上此参数用户将使用授权的账户进行支付
        // orderInfo += "&extern_token=" + "\"" + extern_token + "\"";

        // 支付宝处理完请求后，当前页面跳转到商户指定页面的路径，可空
        orderInfo += "&return_url=\"m.alipay.com\"";

        // 调用银行卡支付，需配置此参数，参与签名， 固定值 （需要签约《无线银行卡快捷支付》才能使用）
//         orderInfo += "&paymethod=\"expressGateway\"";
        return orderInfo;
    }


    /**
     * 生成订单号     订单号由服务器生成
     * 201602170000000 生成商户订单号，该值在商户端应保持唯一（可自定义格式规范）
     *
     * @return
     */

    public String getOutTradeNo() {
        SimpleDateFormat format = new SimpleDateFormat("MMddHHmmss", Locale.getDefault());

        Date date = new Date();
        String key = format.format(date);

        Random r = new Random();
        key = key + r.nextInt();
        key = key.substring(0, 15);
        return key;
    }


    /**
     * 获取签名方式
     */
    private String getSignType() {
        return "sign_type=\"RSA\"";
    }

}
