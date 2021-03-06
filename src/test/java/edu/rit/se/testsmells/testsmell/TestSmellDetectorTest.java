package edu.rit.se.testsmells.testsmell;

import com.github.javaparser.ast.CompilationUnit;
import edu.rit.se.testsmells.testsmell.smell.AssertionRoulette;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TestSmellDetectorTest {
    private static final String TSStubName = "test";
    TestSmellDetector sut;

    @BeforeEach
    void setUp() {
        this.sut = TestSmellDetector.createTestSmellDetector();
        this.sut.addDetectableSmell(new TestSmellStub());
    }

    @Test
    void getTestSmellNames() {
        AssertionRoulette smell = new AssertionRoulette();
        this.sut.addDetectableSmell(smell);

        List<String> testSmellNames = this.sut.getTestSmellNames();

        assertTrue(testSmellNames.contains(TSStubName));
        assertTrue(testSmellNames.contains(smell.getSmellName()));
    }

    @Test
    void detectSmells() throws IOException {
        TestFile tf = new TestFileStub("app", "", "");
        List<AbstractSmell> smells;

        this.sut.detectSmells(tf);
        smells = tf.getTestSmells();

        assertEquals(smells.size(), 1);
        assertFalse(smells.get(0).hasSmell());
    }

    private static class TestFileStub extends TestFile {
        public TestFileStub(String app, String testFilePath, String productionFilePath) {
            super(app, testFilePath, productionFilePath);
        }

        @Override
        protected void checkValidity(String testPath, String prodPath, String app) {
        }

        @Override
        public String getTestFileNameWithoutExtension() {
            return "";
        }

        @Override
        public String getProductionFileNameWithoutExtension() {
            return "";
        }
    }

    private static class TestSmellStub extends AbstractSmell {

        @Override
        public String getSmellName() {
            return TSStubName;
        }

        @Override
        public AbstractSmell recreate() {
            return new TestSmellStub();
        }

        @Override
        public void runAnalysis(CompilationUnit testFileCompilationUnit, CompilationUnit productionFileCompilationUnit, String testFileName, String productionFileName) throws FileNotFoundException {
            throw new FileNotFoundException("test");
        }
    }
}