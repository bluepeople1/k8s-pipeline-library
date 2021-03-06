package com.ft.jenkins.provision;

import com.ft.jenkins.EnvType;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ProvisionerUtilUnitTest {

  ProvisionerUtil provisionerUtil = new ProvisionerUtil();

  @Test
  public void testClusterUpdateInfoForNull() {
    assertNull(provisionerUtil.getClusterUpdateInfo(null));
  }

  @Test
  public void testClusterUpdateInfoForInCompleteName() {
    assertThrows(IllegalArgumentException.class, () -> provisionerUtil.getClusterUpdateInfo("upp-eu"));
  }

  @Test
  public void testClusterUpdateInfoForTestEnv() {
    ClusterUpdateInfo updateInfo = provisionerUtil.getClusterUpdateInfo("upp-k8s-dev-delivery-eu");
    assertAll("update info properties",
              () -> assertEquals("upp", updateInfo.getPlatform()),
              () -> assertEquals("eu", updateInfo.getRegion()),
              () -> assertEquals(EnvType.DEVELOPMENT, updateInfo.getEnvType()),
              () -> assertEquals("k8s-dev", updateInfo.getEnvName()),
              () -> assertEquals("delivery", updateInfo.getCluster()),
              () -> assertEquals("https://upp-k8s-dev-delivery-eu-dex.ft.com", updateInfo.getOidcIssuerUrl())
    );
  }

  @Test
  public void testClusterUpdateInfoForProdEnv() {
    ClusterUpdateInfo updateInfo = provisionerUtil.getClusterUpdateInfo("pac-prod-delivery-eu");
    assertAll("update info properties",
              () -> assertEquals("pac", updateInfo.getPlatform()),
              () -> assertEquals("eu", updateInfo.getRegion()),
              () -> assertEquals(EnvType.PROD, updateInfo.getEnvType()),
              () -> assertEquals("prod", updateInfo.getEnvName()),
              () -> assertEquals("https://pac-prod-delivery-eu-dex.ft.com", updateInfo.getOidcIssuerUrl()),
              () -> assertEquals("delivery", updateInfo.getCluster())
    );
  }

  @Test
  public void testClusterUpdateInfoForStagingEnv() {
    ClusterUpdateInfo updateInfo = provisionerUtil.getClusterUpdateInfo("upp-staging-delivery-eu");
    assertAll("update info properties",
              () -> assertEquals("upp", updateInfo.getPlatform()),
              () -> assertEquals("eu", updateInfo.getRegion()),
              () -> assertEquals(EnvType.TEST, updateInfo.getEnvType()),
              () -> assertEquals("staging", updateInfo.getEnvName()),
              () -> assertEquals("delivery", updateInfo.getCluster())
    );
  }

  @Test
  public void testClusterUpdateInfoForSimpleTestEnv() {
    ClusterUpdateInfo updateInfo = provisionerUtil.getClusterUpdateInfo("upp-devcj-publish-us");
    assertAll("update info properties",
              () -> assertEquals("upp", updateInfo.getPlatform()),
              () -> assertEquals("us", updateInfo.getRegion()),
              () -> assertEquals(EnvType.DEVELOPMENT, updateInfo.getEnvType()),
              () -> assertEquals("devcj", updateInfo.getEnvName()),
              () -> assertEquals("https://upp-devcj-publish-us-dex.ft.com", updateInfo.getOidcIssuerUrl()),
              () -> assertEquals("publish", updateInfo.getCluster())
    );
  }

  @Test
  public void testClusterUpdateInfoForEnvWithoutCluster() {
    ClusterUpdateInfo updateInfo = provisionerUtil.getClusterUpdateInfo("pac-staging-us");
    assertAll("update info properties",
              () -> assertEquals("pac", updateInfo.getPlatform()),
              () -> assertEquals("us", updateInfo.getRegion()),
              () -> assertEquals(EnvType.TEST, updateInfo.getEnvType()),
              () -> assertEquals("staging", updateInfo.getEnvName()),
              () -> assertEquals("https://pac-staging-us-dex.ft.com", updateInfo.getOidcIssuerUrl()),
              () -> assertEquals("", updateInfo.getCluster())
    );
  }

  @Test
  public void testClusterUpdateInfoForEnvWithoutClusterAndComposedName() {
    ClusterUpdateInfo updateInfo = provisionerUtil.getClusterUpdateInfo("pac-k8s-dev-test-us");
    assertAll("update info properties",
              () -> assertEquals("pac", updateInfo.getPlatform()),
              () -> assertEquals("us", updateInfo.getRegion()),
              () -> assertEquals(EnvType.DEVELOPMENT, updateInfo.getEnvType()),
              () -> assertEquals("k8s-dev-test", updateInfo.getEnvName()),
              () -> assertEquals("https://pac-k8s-dev-test-us-dex.ft.com", updateInfo.getOidcIssuerUrl()),
              () -> assertEquals("", updateInfo.getCluster())
    );
  }
}
