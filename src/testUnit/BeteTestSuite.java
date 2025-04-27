package testUnit;

import org.junit.runners.Suite;
import org.junit.runner.RunWith;

/**
 * This is the global test suite of unit tests.
 * 
 * It includes two test cases : {@link TestTreeBuild} and {@link TestTreeVisitor}.
 * 
 * @author Tianxiao.Liu@u-cergy.fr
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({CombatTest.class, ReproductionTest.class, SimulationBuilderTest.class, StatisticsCollectorTest.class, BeteTest.class})
public class BeteTestSuite {

}
