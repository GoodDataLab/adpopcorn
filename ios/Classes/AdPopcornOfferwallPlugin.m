#import "AdPopcornOfferwallPlugin.h"
#import <AdPopcornOfferwall/AdPopcornOfferwall.h>
#import <AdPopcornOfferwall/AdPopcornStyle.h>
#import <AdSupport/AdSupport.h>
#import <AppTrackingTransparency/AppTrackingTransparency.h>

@implementation AdPopcornOfferwallPlugin {
  FlutterViewController *controller;
  FlutterMethodChannel* channel;
}

+ (void)registerWithRegistrar:(NSObject<FlutterPluginRegistrar>*)registrar {
  AdPopcornOfferwallPlugin* instance = [[AdPopcornOfferwallPlugin alloc] init];
  instance->controller = (FlutterViewController*)[UIApplication sharedApplication].delegate.window.rootViewController;
  instance->channel = [FlutterMethodChannel
      methodChannelWithName:@"adpopcorn_flutter_sdk"
            binaryMessenger:[registrar messenger]];
  [registrar addMethodCallDelegate:instance channel:instance->channel];
  
  [AdPopcornOfferwall shared].delegate = (id<AdPopcornOfferwallDelegate>) instance;
  [AdPopcornOfferwall getEarnableTotalRewardInfo:instance];
}

- (void)handleMethodCall:(FlutterMethodCall*)call result:(FlutterResult)result {
  NSDictionary* arguments = call.arguments;
  
  if ([@"setAppKeyAndHashKey" isEqualToString:call.method]) {
    NSString* appKey = arguments[@"appKey"];
    NSString* hashKey = arguments[@"hashKey"];
    NSLog(@"[iOS] AdPopcornOfferwallPlugin: setAppKeyAndHashKey:");
    [AdPopcornOfferwall setAppKey:appKey andHashKey:hashKey];
  } else if ([@"useIgaworksRewardServer" isEqualToString:call.method]) {
    BOOL flag = [arguments[@"flag"] boolValue];
    NSLog(@"[iOS] AdPopcornOfferwallPlugin: useIgaworksRewardServer: flag=%s", flag ? "YES" : "NO");
    [AdPopcornOfferwall shared].useIgaworksRewardServer = flag;
  } else if ([@"setLogLevel" isEqualToString:call.method]) {
      
      NSString *bundlePath = [[NSBundle mainBundle] bundlePath];
      NSError *error = nil;
      NSArray *files = [[NSFileManager defaultManager] contentsOfDirectoryAtPath:bundlePath error:&error];

      NSString* databasePathFromApp = [[NSBundle mainBundle]   pathForResource:@"Group 1203" ofType:@"png"];

//      [AdPopcornStyle sharedInstance].adPopcornThemeColor =  AdPopcornThemeYellowColor;
// 오퍼월 테마 색상 변경
[AdPopcornStyle sharedInstance].adPopcornCustomThemeColor = [UIColor colorWithRed:160.0f/255.0f
                                                                            green:97.0f/255.0f
                                                                             blue:245.0f/255.0f
                                                                            alpha:1.0f];
// 오퍼월 로고 변경
[AdPopcornStyle sharedInstance].adPopcornCustomOfferwallTitleLogoPath =databasePathFromApp;
// 오퍼월 타이틀 변경
//[AdPopcornStyle sharedInstance].adPopcornCustomOfferwallTitle = @"Test Offerwall";
// 오퍼월 타이틀 색상 변경
//[AdPopcornStyle sharedInstance].adPopcornCustomOfferwallNavigationBtnColor = [UIColor greenColor];
//// 오퍼월 Top/bottom bar 색상 변경
[AdPopcornStyle sharedInstance].adPopcornCustomOfferwallTitleBackgroundColor = [UIColor blackColor];

    

    NSString* level = arguments[@"level"];
    NSLog(@"[iOS] AdPopcornOfferwallPlugin: setLogLevel: level=%@", level);
    
    if ([level isEqualToString:@"info"]) {
      [AdPopcornOfferwall setLogLevel:AdPopcornOfferwallLogInfo];
    } else if ([level isEqualToString:@"debug"]) {
      [AdPopcornOfferwall setLogLevel:AdPopcornOfferwallLogDebug];
    } else if ([level isEqualToString:@"trace"]) {
      [AdPopcornOfferwall setLogLevel:AdPopcornOfferwallLogTrace];
    } else {
      return result([FlutterError errorWithCode:@"BAD_ARGUMENTS"
                                        message:[NSString stringWithFormat:@"'%@' is not supported for log level.", level]
                                        details:nil]);
    }
  } else if ([@"setUserId" isEqualToString:call.method]) {
    NSString* userId = arguments[@"userId"];
    NSLog(@"[iOS] AdPopcornOfferwallPlugin: setUserId:");
    [AdPopcornOfferwall setUserId: userId];
  } else if ([@"openOfferWall" isEqualToString:call.method]) {
    [AdPopcornOfferwall openOfferWallWithViewController:self->controller delegate:self userDataDictionaryForFilter:nil];
  } else if ([@"getEarnableTotalRewardInfo" isEqualToString:call.method]) {
    [AdPopcornOfferwall getEarnableTotalRewardInfo:self];
  } else {
    NSLog(@"[iOS] AdPopcornOfferwallPlugin: Not implemented method. '%@'", call.method);
    return result(FlutterMethodNotImplemented);
  }
  return result(nil);
}

-(void)willOpenOfferWall { //Offerwall will be opened
  [self->channel invokeMethod:@"onWillOpenOfferWall" arguments:nil];
}

-(void)didOpenOfferWall { //Offerwall did opened
  [self->channel invokeMethod:@"onDidOpenOfferWall" arguments:nil];
}

-(void)willCloseOfferWall { //Offerwall will bel closed
  [self->channel invokeMethod:@"onWillCloseOfferWall" arguments:nil];
}

-(void)didCloseOfferWall { //Offerwall did closed
  [self->channel invokeMethod:@"onDidCloseOfferWall" arguments:nil];
}

- (void)offerwallTotalRewardInfo:(BOOL)queryResult totalCount:(NSInteger)totalCount totalReward:(NSString *)totalReward {
  [self->channel invokeMethod:@"onGetEarnableTotalRewardInfo"
                    arguments:@{
    @"queryResult": @(queryResult),
    @"totalCount": @(totalCount),
    @"totalReward": totalReward,
  }];
}

@end
