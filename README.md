# AdPopcorn SDK for Flutter

[애드팝콘](https://www.adpopcorn.com/) Flutter SDK

## Getting Started

이 플러그인은 Android와 iOS로 분리되어 있는 애드팝콘의 SDK를 자동으로 import하고, 각 native의 함수들을 호출하는 Flutter 함수를 제공합니다.

다만 기존 함수의 wrapper일 뿐이기 때문에 프로젝트 설정 및 함수 사용법 등 SDK 연동에 관해서는 [애드팝콘 연동가이드](https://adpopcorn.notion.site/SDK-af84236e11194a99ac6652f61cfaa669)를 반드시 참고해야 합니다.


## Issues
애드팝콘의 Android, iOS SDK는 각자 제공하는 함수가 다릅니다. 이 플러그인에서는 각 플랫폼의 함수 대부분을 인터페이스로 가지고 있습니다. 만약 실행 중인 플랫폼에서 지원하지 않는 함수를 호출할 경우 Flutter 레벨에서 `UnimplementedError`를 throw합니다.

플랫폼별 지원 함수는 다음과 같습니다.(2022.06.13 현재)

|                            | Android | iOS |
|:--------------------------:|:-------:|:---:|
|          setUserId         |    O    |  O  |
|        openOfferwall       |    O    |  O  |
| getEarnableTotalRewardInfo |    O    |  O  |
|      setOnAgreePrivacy     |    O    |  X  |
|    setOnDisagreePrivacy    |    O    |  X  |
|  setOnClosedOfferWallPage  |    O    |  X  |
|   setOnWillOpenOfferWall   |    X    |  O  |
|    setOnDidOpenOfferWall   |    X    |  O  |
|   setOnWillCloseOfferWall  |    X    |  O  |
|   setOnDidCloseOfferWall   |    X    |  O  |
|    useFlagShowWhenLocked   |    O    |  X  |
|         openCSPage         |    O    |  X  |
|         loadPopupAd        |    O    |  X  |
|         showPopupAd        |    O    |  X  |
|     setAppKeyAndHashKey    |    X    |  O  |
|   useIgaworksRewardServer  |    X    |  O  |
|         setLogLevel        |    X    |  O  |

위 테이블에는 없으나 애드팝콘 SDK에서는 제공하는 함수가 있을 수 있습니다.
