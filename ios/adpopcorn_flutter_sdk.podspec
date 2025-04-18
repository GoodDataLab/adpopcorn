#
# To learn more about a Podspec see http://guides.cocoapods.org/syntax/podspec.html.
# Run `pod lib lint adpopcorn_flutter_sdk.podspec` to validate before publishing.
#
require 'yaml'

pubspec = YAML.load_file(File.join('..', 'pubspec.yaml'))
library_version = pubspec['version'].gsub('+', '-')

Pod::Spec.new do |s|
  s.name             = 'adpopcorn_flutter_sdk'
  s.version          = library_version
  s.summary          = 'AdPopcorn Flutter SDK'
  s.homepage         = 'https://github.com/Crepay/adpopcorn_flutter_sdk'
  s.license          = { :file => '../LICENSE' }
  s.author           = { 'Crepay, Inc.' => 'neo.kim@crepay.com' }
  s.source           = { :path => '.' }
  s.source_files = 'Classes/**/*'
  s.resources = ['SDK/*.png']
  s.public_header_files = 'Classes/**/*.h'
  s.dependency 'Flutter'
  s.dependency 'AdPopcornOfferwall', '~> 4.2.3'
  s.platform = :ios, '9.0'
  s.static_framework = true

  # Flutter.framework does not contain a i386 slice.
  s.pod_target_xcconfig = { 'DEFINES_MODULE' => 'YES', 'EXCLUDED_ARCHS[sdk=iphonesimulator*]' => 'i386' }
end
