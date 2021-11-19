#
# To learn more about a Podspec see http://guides.cocoapods.org/syntax/podspec.html.
# Run `pod lib lint aliyun_face.podspec` to validate before publishing.
#
Pod::Spec.new do |s|
  s.name             = 'aliyun_face'
  s.version          = '0.0.1'
  s.summary          = 'A new flutter plugin project.'
  s.description      = <<-DESC
A new flutter plugin project.
                       DESC
  s.homepage         = 'http://example.com'
  s.license          = { :file => '../LICENSE' }
  s.author           = { 'Your Company' => 'email@example.com' }
  s.source           = { :path => '.' }
  s.source_files = 'Classes/**/*'
  s.dependency 'Flutter'
  s.vendored_frameworks = 'Framework/*.framework'
  s.frameworks = 'CoreGraphics', 'Accelerate', 'SystemConfiguration', 'AssetsLibrary', 'CoreTelephony', 'QuartzCore', 'CoreFoundation', 'CoreLocation', 'ImageIO', 'CoreMedia', 'CoreMotion', 'AVFoundation', 'WebKit', 'AudioToolbox', 'CFNetwork', 'MobileCoreServices', 'AdSupport', 'libresolv', 'libz', 'libc++', 'libc++abi', 'libc++_1', 'libz_1_2_8'
  s..pod_target_xcconfig = { 'OTHER_LDFLAGS' => '-lObjC,-lxml2,-framework,"BioAuthAPI"' }
  s.platform = :ios, '9.0'

  # Flutter.framework does not contain a i386 slice.
  s.pod_target_xcconfig = { 'DEFINES_MODULE' => 'YES', 'EXCLUDED_ARCHS[sdk=iphonesimulator*]' => 'i386' }
  s.swift_version = '5.0'
end
