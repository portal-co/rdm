package pc.portal.rdm;

public class RDM {
    public static enum RDMResult {
        CLEAN, INFECTED, UNKNOWN
    }

    public static enum RDMEnvironment {
        STANDARD, MINECRAFT
    }

    public static final void actOn(RDMResult result, RDMEnvironment environment) {
        switch (result) {
            case INFECTED:
                System.err.println("\n" +

                        "ERROR: Native code detected where it shouldn't be! Exiting.\n" +
                        "This software must be run without native code present.\n" +
                        "Please remove any native libraries and try again.\n\n" +
                        "This can be mitigated by:\n" +
                        "- NOT using the native code\n" +
                        "- Lifting the native code to WASM and then JVM bytecode, forming a safe execution environment\n"
                        +
                        "- Using a different, pure-Java implementation of the same functionality.\n");
                switch (environment) {
                    case STANDARD:
                        break;
                    case MINECRAFT:
                        System.err.println(
                                "It is shunned in the Minecraft community to use native code in mods, as it can lead to instability, incompatibility, and security risks. Please ensure that your mod does not include any native libraries.");
                        break;
                }
                System.exit(1);
                break;
            case UNKNOWN:
                System.err.println("Warning: RDM scan result unknown.");
                break;
            case CLEAN:
                break;
        }
    }

    public static final RDMResult scanClasses() {
        for (String nativeString : new String[] { "cyb0124.NativeLoader" }) {

            try {
                Class.forName(nativeString);
                return RDMResult.INFECTED;
            } catch (ClassNotFoundException e) {
            }
        }
        ClassLoader loader = RDM.class.getClassLoader();
        if (loader != null) {
            for (String packageString : new String[] { "cyb0124" }) {
                if (loader.getDefinedPackage(packageString) != null) {
                    return RDMResult.INFECTED;
                }
            }
        } else {
            return RDMResult.UNKNOWN;
        }
        return RDMResult.CLEAN;
    }
}
