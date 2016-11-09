<?php
function error($ecode,$emsg){
	$ert = "<?xml version=\"1.0\" encoding=\"utf-8\" ?>\n<response>\n\t<result>".$ecode."</result>\n\t<message>".$emsg."</message>\n</response>";
	return $ert;
	}
$uri = "http://api.chanyoo.cn/utf8/interface/send_sms.aspx";
if($_SERVER["REMOTE_ADDR"]!='58.47.143.7'){
	$ecode = -104;
	$emsg = '禁止从此IP请求';
	echo error($ecode,$emsg);
	return;
	}
$num = $_GET['num'];
$yzm = $_GET['yzm'];
$msg = $_GET['msg'];
if($_POST['id']=='test'){
$num = $_POST['num'];
$yzm = $_POST['yzm'];
$msg = $_POST['msg'];
}
$data = array (
	'username'	=> 'neozhao', 
	'password'	=> 'IcEBZQqT',
	'receiver'	=> $num,
	'content'	=> '验证码：'.$yzm.'。您正'.$msg.'，打死也不要告诉别人哦！'
);
$ch = curl_init ();
curl_setopt ( $ch, CURLOPT_URL, $uri );
curl_setopt ( $ch, CURLOPT_POST, 1 );
curl_setopt ( $ch, CURLOPT_HEADER, 0 );
curl_setopt ( $ch, CURLOPT_RETURNTRANSFER, 1 );
curl_setopt ( $ch, CURLOPT_POSTFIELDS, $data );
$return = curl_exec ( $ch );
curl_close ( $ch );
print_r($return);
?>