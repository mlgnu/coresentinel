package com.districtcore.coresentinel.bootstrap;

//@Component
//public class AdminInitializer implements CommandLineRunner {
//        private final UserRepository userRepository;
//        private final PasswordEncoder passwordEncoder;
//        private final AdminConfig adminConfig;
//
//        public AdminInitializer(UserRepository userRepository, PasswordEncoder passwordEncoder, AdminConfig adminConfig) {
//            this.userRepository = userRepository;
//            this.passwordEncoder = passwordEncoder;
//            this.adminConfig = adminConfig;
//        }
//
//        @Override
//        public void run(String... args) {
//            boolean isAdminPresent = this.userRepository.findByUsername(adminConfig.getUsername()).isPresent();
//            if (!isAdminPresent) {
//                User admin = new User();
//                admin.setUsername(adminConfig.getUsername());
//                admin.setEmail(adminConfig.getEmail());
//                admin.setFullName("Admin");
//                admin.setPassword(passwordEncoder.encode(adminConfig.getPassword()));
//                admin.setRole(UserRole.ADMIN);
//                userRepository.save(admin);
//            }
//    }
//}
